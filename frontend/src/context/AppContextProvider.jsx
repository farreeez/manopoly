import React from "react";
import { useState, useEffect } from "react";

export const AppContext = React.createContext({});

export function AppContextProvider({ children }) {
  const [player, setPlayer] = useState({
      name: "",
      id: -1,
      isLoggedIn: false,
      boardId: -1,
      colour: -1,
    });

    const [board, setBoard] = useState();

  const context = {
    player,
    setPlayer,
    board,
    setBoard
  };

  return <AppContext.Provider value={context}>{children}</AppContext.Provider>;
}
