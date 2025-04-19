import "./css/PropertyCard.css";
import { X } from "lucide-react";

export default function PropertyCard({ setDisplayProperty }) {
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
          <div className="propertyColour"></div>
        </div>
      </div>
    </div>
  );
}
