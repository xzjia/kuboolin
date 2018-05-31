<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h2>Current counter value: {{ currentValue }}</h2>
    <button @click="counterUp">UP</button>
    <button @click="counterDown">DOWN</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Counter",
  data: () => ({
    msg: "Kuboolin board",
    currentValue: "Loading"
  }),
  methods: {
    counterUp: function() {
      axios
        .put(`/api/counter/up`)
        .then(response => (this.currentValue = response.data.value));
    },
    counterDown: function() {
      axios
        .put(`/api/counter/down`)
        .then(response => (this.currentValue = response.data.value));
    }
  },
  created: function() {
    const sse = new EventSource("http://localhost:8080/api/counter");
    sse.onmessage = event => {
      this.currentValue = JSON.parse(event.data).value;
    };
    axios.get(`/hello`).then(response => (this.msg = response.data));
    axios
      .get(`/api/counter`)
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
  padding: 15px 32px;
  margin: 2em 2em;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  border-radius: 5px;
  width: 25%;
}
</style>
