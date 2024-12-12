import "./css/ColourSelection.css";

function chooseColour(colour, setPlayer) {
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

function ColourSelection({ possibleColours, takenColours, player, setPlayer }) {
  return (
    <div className="modal">
      <div className="overlay"></div>
      <div className="modal-content">
        <h1 className="chooseColour">Choose your colour</h1>
        <div className="colours">
          {possibleColours.map((colour) =>
            !takenColours
              .map((colour) => Number(colour.identifier))
              .includes(colour.identifier) ? (
              <div
                className="circle"
                onClick={() => chooseColour(colour, setPlayer)}
                style={{
                  backgroundColor: `rgb(${colour.red},${colour.green},${colour.blue})`,
                }}
              ></div>
            ) : (
              <div
                className="circle"
                style={{
                  backgroundColor: `rgb(${colour.red},${colour.green},${colour.blue})`,
                  opacity: 0.2,
                }}
              ></div>
            )
          )}
        </div>
      </div>
    </div>
  );
}

export default ColourSelection;
