import "./css/HomePage.css";
import { createBoard, joinBoard } from "../services/BoardServices";
import { useContext } from "react";
import { AppContext } from "../context/AppContextProvider";

function goToBoard(player, setPlayer) {
  const textBox = document.getElementById("boardCodeTextBox");
  let boardCode;

  if (textBox) {
    boardCode = textBox.value;
    textBox.value = "";
  } else {
    alert("invalid text box id used in function joinBoard in HomePage.tsx");
  }

  if (!boardCode) {
    alert("please enter a valid name.");
    return;
  }

  joinBoard(boardCode, player, setPlayer);
}

function HomePage() {
  const { player, setPlayer } = useContext(AppContext);
  return (
    <div id="homePage">
      <h1 id="greetingPlayer">Hi {player.name}!</h1>
      <input
        type="text"
        id="boardCodeTextBox"
        placeholder="Enter Board Code"
      ></input>
      <div id="buttonContainer">
        <button
          id="joinBoard"
          className="button"
          onClick={() => goToBoard(player, setPlayer)}
        >
          Join Board
        </button>
        <button
          id="createBoard"
          className="button"
          onClick={() => createBoard(player, setPlayer)}
        >
          Create New Board
        </button>
      </div>
    </div>
  );
}

export default HomePage;
