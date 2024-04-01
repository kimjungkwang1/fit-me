import React from 'react';

export default function FeedListUser() {
  const imageNumbers = Array.from({ length: 41 }, (_, i) => i + 1);
  return (
    <>
      <div className='grid grid-cols-3 justify-between mx-[2vw]'>
        {imageNumbers.map((num) => (
          <div className='m-1 h-64 w-42 flex justify-center items-center rounded-md border-solid border-[1px] border-gray-400'>
            <img
              className='max-w-full max-h-full object-contain'
              src={`https://fit-me.site/images/feed/ootd/ootd${num}.png`}
              alt=''
            />
          </div>
        ))}
      </div>
    </>
  );
}
