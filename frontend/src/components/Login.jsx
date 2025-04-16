import "./css/Login.css";
import { createPlayer } from "../services/PlayerServices";

function joinGame(setPlayer) {
  const textBox = document.getElementById("usernameInput");
  let name;

  if (textBox) {
    name = textBox.value;
    textBox.value = "";
  } else {
    alert("invalid text box id used in function joinGame in Login.tsx");
  }

  if (!name) {
    alert("please enter a valid name.");
    return;
  }

  createPlayer(name, setPlayer);
}

function Login({ setPlayer }) {
  return (
    <div id="login">
      <input type="text" id="usernameInput" placeholder="Enter Username"></input>
      <button id="loginButton" className="button" onClick={() => joinGame(setPlayer)}>
        Login
      </button>
    </div>
  );
}

export default Login;
