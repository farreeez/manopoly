import React from "react";
import logo from "./logo.svg";
import "./App.css";

function joinGame() {
  const fetchPromise = fetch("http://localhost:8080/players/joinGame", {
    method: "GET",
  });

  const streamPromise = fetchPromise.then((response) => response.text());
  streamPromise.then((data) => alert(data));
}

function App() {
  return (
    <div className="App">
      <button onClick={joinGame}>login</button>
    </div>
  );
}

export default App;
