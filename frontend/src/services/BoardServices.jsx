import { updatePositions } from "../components/BoardComponents/Board";

export function createBoard(player, setPlayer) {
  fetch("http://localhost:8080/board/createBoard", {
    method: "POST",
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Could not create a new board.");
      }

      return response.json();
    })
    .then((data) => {
      let newPlayer = {
        name: player.name,
        id: Number(player.id),
        isLoggedIn: player.isLoggedIn,
        boardId: Number(data.id),
        colour: data.colour,
      };
      setPlayer(newPlayer);
    })
    .catch((error) => console.error(error));
}

export function joinBoard(boardCode, player, setPlayer) {
  fetch("http://localhost:8080/board/joinBoard/" + boardCode, {
    method: "POST",
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Invalid Board Code.");
      }

      return response.json();
    })
    .then((data) => {
      let newPlayer = {
        name: player.name,
        id: Number(player.id),
        isLoggedIn: player.isLoggedIn,
        boardId: Number(data.id),
        colour: data.colour,
      };
      setPlayer(newPlayer);
    })
    .catch((error) => console.error(error));
}

export function getBoard(player, setSquares, setBoard) {
  fetch("http://localhost:8080/board/getBoard/" + player.boardId, {
    method: "GET",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Board does not exist.");
      }
      return response.json();
    })
    .then((data) => {
      console.log(data.squareIds)
      setSquares(data.squareIds);
      setBoard(data);
      updatePositions(data.playerIds);
    })
    .catch((error) => console.error(error));
}

export function leaveBoard(player, setPlayer, setBoard) {
  fetch("http://localhost:8080/board/leaveBoard", {
    method: "DELETE",
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Player does not exist (cannot leave board).");
      }
      return response;
    })
    .then(() => {
      setPlayer({
        name: player.name,
        id: Number(player.id),
        isLoggedIn: true,
        boardId: -1,
        colour: undefined,
      });

      setBoard(null);
    })
    .catch((error) => console.error(error));
}

export function getsquare(squareId, setSquare, setProperty, getProperty) {
  fetch("http://localhost:8080/board/getBoardSquare/" + squareId, {
    method: "GET",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Board does not exist.");
      }
      return response.json();
    })
    .then((data) => {
      setSquare(data);

      if (data.property) {
        getProperty(data.id, setProperty);
      }
    })
    .catch((error) => console.error(error));
}

export function chooseColour(colour, setPlayer) {
  fetch("http://localhost:8080/board/chooseColour/" + colour.identifier, {
    method: "POST",
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Could not create a new player.");
      }

      return response.json();
    })
    .then((data) => {
      setPlayer({
        name: data.name,
        id: Number(data.id),
        isLoggedIn: true,
        boardId: Number(data.boardId),
        colour: data.colour,
      });
    })
    .catch((error) => console.error(error));
}

export function endTurn(setDisplayBuyAfterRoll) {
  fetch("http://localhost:8080/board/endTurn", {
    method: "POST",
    credentials: "include",
  })
    .then(async (response) => {
      if (!response.ok) {
        const message = await response.text();
        console.error("Error message from response:", message);
      } else {
        setDisplayBuyAfterRoll(false);
      }
    })
    .catch((error) => console.error(error));
}

export function fetchDiceData(setCardActionData) {
  fetch("http://localhost:8080/board/rollDice", {
    method: "POST",
    credentials: "include",
  })
    .then(async (response) => {
      if (!response.ok) {
        const message = await response.text();
        console.error("Error message from response:", message);
      }

      return response.json();
    })
    .then((data) => {
      setCardActionData(data);
    })
    .catch((error) => console.error(error));
}
