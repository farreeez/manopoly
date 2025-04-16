import "./css/ColourSelection.css";
import { chooseColour } from "../../services/BoardServices";


function ColourSelection({ possibleColours, takenColours, player, setPlayer }) {
  return (
    <div className="modal">
      <div className="overlay">
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
    </div>
  );
}

export default ColourSelection;
