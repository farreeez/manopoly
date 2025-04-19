import React from "react";
import { useState} from "react";

export const BoardContext= React.createContext({});

export function BoardContextProvider({ children }) {
  const [modalProperty, setModalProperty] = useState();

  const context = {
    modalProperty,
    setModalProperty
  };


  return <BoardContext.Provider value={context}>{children}</BoardContext.Provider>;
}
