import "./css/ColourSelection.css";

function ColourSelection({ possibleColours }) {
  return (
    <div className="modal">
      <div className="overlay"></div>
      <div className="modal-content">
        <h1 className="chooseColour">Choose your colour</h1>
        <div className="colours">
          {possibleColours.map((colour) => (
            <div className="circle" style={{backgroundColor: `rgb(${colour.red},${colour.green},${colour.blue})`}}></div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default ColourSelection;
