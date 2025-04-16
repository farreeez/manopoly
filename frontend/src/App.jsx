import React, { useContext, useEffect } from "react";
import Login from "./components/Login";
import { useState } from "react";
import { checkCookie } from "./services/PlayerServices";
import "./App.css";
import HomePage from "./components/HomePage";
import Board from "./components/BoardComponents/Board";
import { AppContext } from "./context/AppContextProvider";

function App() {
  const {player, setPlayer} = useContext(AppContext);

  useEffect(() => {
    checkCookie(setPlayer);
  }, []);

  // make sure player goes to correct board if a board is already allocated
  return (
    <div className="App">
      {player.isLoggedIn ? (
        Number(player.boardId) === -1 ? (
          <HomePage/>
        ) : (
          <Board/>
        )
      ) : (
        <Login/>
      )}
    </div>
  );
}

export default App;
