import React, { useEffect } from "react";
import Login from "./components/Login";
import { useState } from "react";
import "./App.css";
import HomePage from "./components/HomePage";
import Board from "./components/BoardComponents/Board";

function App() {
  const [player, setPlayer] = useState({
    name: "",
    id: -1,
    isLoggedIn: false,
    boardId: -1,
    colour: -1,
  });

  useEffect(() => {
    fetch("http://localhost:8080/players/checkCookie", {
      method: "GET",
      credentials: "include",
    })
      .then((response) => {
        if (!response.ok) {
          if (document.cookie) {
            // Get all cookies and split into array
            const cookies = document.cookie.split(";");

            // Clear all cookies
            for (let cookie of cookies) {
              const cookieName = cookie.split("=")[0].trim();
              document.cookie = `${cookieName}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
            }
          }
          throw new Error("Could not create a new player.");
        }

        return response.text();
      })
      .then((data) => {
        if (data) {
          data = JSON.parse(data);

          let newPlayer = {
            name: data.name,
            id: Number(data.id),
            isLoggedIn: true,
            boardId: Number(data.boardId),
            colour: data.colour,
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
        Number(player.boardId) === -1 ? (
          <HomePage player={player} setPlayer={setPlayer} />
        ) : (
          <Board player={player} setPlayer={setPlayer} />
        )
      ) : (
        <Login player={player} setPlayer={setPlayer} />
      )}
    </div>
  );
}

export default App;
