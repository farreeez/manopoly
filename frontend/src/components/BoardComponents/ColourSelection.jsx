import "./css/ColourSelection.css";
import { chooseColour } from "../../services/BoardServices";
import { useContext } from "react";
import { AppContext } from "../../context/AppContextProvider";

function ColourSelection({ possibleColours, takenColours }) {
  const { setPlayer } = useContext(AppContext);
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
