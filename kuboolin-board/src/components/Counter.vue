<template>
  <div class="hello">
    <h1>Serving from {{ servedBy }}</h1>
    <h2>Current counter value: {{ currentValue }}</h2>
    <button @click="counterUp">UP</button>
    <button @click="counterDown">DOWN</button>
  </div>
</template>

<script>
import axios from "axios";

const apiEndpoint = "";

export default {
  name: "Counter",
  data: () => ({
    servedBy: "Loading",
    currentValue: "Loading"
  }),
  methods: {
    counterUp: function() {
      axios
        .put(`${apiEndpoint}/api/counter/up`)
        .then(response => (this.currentValue = response.data.value));
    },
    counterDown: function() {
      axios
        .put(`${apiEndpoint}/api/counter/down`)
        .then(response => (this.currentValue = response.data.value));
    }
  },
  created: function() {
    const sse = new EventSource(`${apiEndpoint}/api/counter`);
    sse.onmessage = event => {
      const res = JSON.parse(event.data);
      this.currentValue = res.value;
      this.servedBy = res.by;
    };
    axios
      .get(`${apiEndpoint}/hello`)
      .then(response => (this.servedBy = response.data));
    axios
      .get(`${apiEndpoint}/api/counter`)
      .then(response => (this.currentValue = response.data.value));
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}

button {
  background-color: #4caf50; /* Green */
  border: none;
  color: white;
  padding: 1em 0.1em;
  margin: 2em 2em;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 1em;
  border-radius: 5px;
  width: 25%;
}
</style>
