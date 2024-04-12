import React from 'react';
import './rating.css';

function Rating({ value }) {
    const maxStars = 5;
    const fullStars = Math.floor(value);
    const remainder = value - fullStars;

    const renderStars = () => {
        const stars = [];
        for (let i = 1; i <= maxStars; i++) {
            if (i <= fullStars) {
                stars.push(<span key={i} className="star filled">&#9733;</span>);
            } else if (i === fullStars + 1 && remainder >= 0.5) {
                stars.push(<span key={i} className="star half-filled">&#9733;</span>);
            } else {
                stars.push(<span key={i} className="star">&#9733;</span>);
            }
        }
        return stars;
    };

    return <div className="rating">{renderStars()}</div>;
}

export default Rating;
