import React from 'react';

export default function FeedListUser() {
  const imageNumbers = Array.from({ length: 41 }, (_, i) => i + 1);
  const columns: number[][] = [[], [], []];
  imageNumbers.forEach((num, i) => {
    columns[i % 3].push(num);
  });

  return (
    <div className='flex justify-between mx-[2vw]'>
      {columns.map((column, idx) => (
        <div key={idx} className='flex flex-col p-1 w-[33.33%]'>
          {column.map((num) => (
            <img
              key={num}
              className='mb-1'
              src={`https://fit-me.site/images/feed/ootd/ootd${num}.png`}
              alt={`ootd ${num}`}
            />
          ))}
        </div>
      ))}
    </div>
  );
}
