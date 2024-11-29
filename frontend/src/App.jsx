import React from "react";
import Login from "./components/Login";
import { useState } from "react";
import "./App.css";
import HomePage from "./components/HomePage";

function App() {
  const [player, setPlayer] = useState({ name: "", id: -1, isLoggedIn: false });

  fetch("http://localhost:8080/players/checkCookie", {
    method: "GET",
    credentials: "include"
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Could not create a new player.");
      }

      return response.text()
    })
    .then((data) => {
      if(data){
        alert(data)
      }
    })
    .catch((error) => console.error(error));

  return (
    <div className="App">
      {player.isLoggedIn ? (
        <HomePage />
      ) : (
        <Login player={player} setPlayer={setPlayer}/>
      )}
    </div>
  );
}

export default App;
