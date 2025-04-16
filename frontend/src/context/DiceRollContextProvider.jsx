import React from "react";
import { useState, useEffect } from "react";

export const DiceRollContext = React.createContext({});

export function DiceRollContextProvider({ children }) {
  const [cardActionData, setCardActionData] = useState(false);
  const [displayBuyAfterRoll, setDisplayBuyAfterRoll] = useState(false);

  const context = {
    cardActionData,
    setCardActionData,
    displayBuyAfterRoll,
    setDisplayBuyAfterRoll,
  };

  return <DiceRollContext.Provider value={context}>{children}</DiceRollContext.Provider>;
}
