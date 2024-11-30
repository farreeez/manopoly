import React, { useEffect } from "react";
import Login from "./components/Login";
import { useState } from "react";
import "./App.css";
import HomePage from "./components/HomePage";

function App() {
  const [player, setPlayer] = useState({ name: "", id: -1, isLoggedIn: false, boardId: -1 });

  useEffect(() => {
    fetch("http://localhost:8080/players/checkCookie", {
      method: "GET",
      credentials: "include",
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Could not create a new player.");
        }

        return response.text();
      })
      .then((data) => {
        if (data) {
          data = JSON.parse(data);

          let newPlayer = {
            name: data.name,
            id: data.playerId,
            isLoggedIn: true,
            boardId: data.boardId,
          };

          setPlayer(newPlayer);
        }
      })
      .catch((error) => console.error(error));
  }, []);

  // make sure player goes to correct board if a board is already allocated
  return (
    <div className="App">
      {player.isLoggedIn ? (
        <HomePage player={player} setPlayer={setPlayer} />
      ) : (
        <Login player={player} setPlayer={setPlayer} />
      )}
    </div>
  );
}

export default App;
