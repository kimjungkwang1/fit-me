import React, { useState } from 'react';

type RatingProps = {
  onRatingChange: (rating: number) => void;
};

const Rating: React.FC<RatingProps> = ({ onRatingChange }) => {
  const [rating, setRating] = useState(0);
  const [hover, setHover] = useState(0);

  const handleRatingChange = (newRating: number) => {
    setRating(newRating);
    onRatingChange(newRating);
  };

  return (
    <>
      <div className='flex justify-center'>
        {[...Array(5)].map((star, index) => {
          index += 1;
          let style = 'text-gray-300'; // 기본적으로 회색 별
          if (index <= hover || (index <= rating && hover === 0)) {
            style = 'text-yellow-500'; // 호버링 또는 레이팅에 따라 노란색 별
          }

          return (
            <button
              key={index}
              className={`h-16 w-16 text-4xl ${style}`}
              onClick={() => handleRatingChange(index)}
              onMouseEnter={() => setHover(index)}
              onMouseLeave={() => setHover(rating)}
            >
              {index <= rating || (index <= hover && hover >= rating) ? '★' : '☆'}
            </button>
          );
        })}
      </div>
    </>
  );
};

export default Rating;
