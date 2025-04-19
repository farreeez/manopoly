import { useEffect, useContext } from "react";
import { BoardContext } from "../../context/BoardContextProvider";
import "./css/PropertyCard.css";
import { X } from "lucide-react";

export default function PropertyCard({ setDisplayProperty, property }) {
  const { modalProperty, setModalProperty } = useContext(BoardContext);

  useEffect(() => {}, [modalProperty]);

  return (
    <div className="property-modal">
      <div
        className="property-overlay"
        onClick={() => {
          setDisplayProperty(false);
        }}
      >
        <div
          className="property-modal-content"
          onClick={(e) => {
            e.stopPropagation();
          }}
        >
          <button
            onClick={() => {
              setDisplayProperty(false);
            }}
            className="close-button"
          >
            <X size={24} />
          </button>
          <div className="propertyColour">{modalProperty.name}</div>

          <ul className="rent-list">
            {modalProperty.rentList.map((rent, index) => (
              <li key={index} className="rent-price">
                <span className="rent-text">{rent.rentPrompt}</span>
                <span className="rent-val">${rent.rentPrice}</span>
              </li>
            ))}
          </ul>


        </div>
      </div>
    </div>
  );
}
