import React from 'react';
import { CiImageOn } from 'react-icons/ci';
import { Button } from 'flowbite-react';

export default function FeedPostForm() {
  return (
    <>
      <div className='flex items-center flex-col'>
        <div className='flex m-2'>
          <div className='w-[30vw] bg-red-50 p-2 text-center'>유저피드</div>
          <div className='w-[30vw] bg-beige p-2 text-center'>매거진</div>
        </div>
        <div className='mb-2'>
          <input type='file' accept='image/*' className='hidden' />
          <div className='w-[90vw] h-[45vw] bg-gray-100 flex justify-center items-center'>
            <CiImageOn size={100}></CiImageOn>
          </div>
        </div>
        <textarea name='' id='' className='w-[90vw] h-[20vw] border'></textarea>
        <Button color='gray' className='my-2 w-[90vw] bg-gray-100'>
          피드등록
        </Button>
      </div>
    </>
  );
}
