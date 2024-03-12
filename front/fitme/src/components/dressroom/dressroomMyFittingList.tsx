import React from 'react';
import DressroomMyfittingShow from './DressroomMyFittingShow';
import { Button } from 'flowbite-react';
import DressroomMyFittingChoose from './DressroomMyFittingChoose';

export default function DressroomMyFittingList() {
  return (
    <>
      <Button className='w-[50%] m-2 ml-auto' color='gray'>
        프로필로 사용
      </Button>
      <div className='flex flex-wrap justify-center'>
        <DressroomMyFittingChoose></DressroomMyFittingChoose>
        <DressroomMyfittingShow></DressroomMyfittingShow>
        <DressroomMyfittingShow></DressroomMyfittingShow>
        <DressroomMyfittingShow></DressroomMyfittingShow>
        <DressroomMyfittingShow></DressroomMyfittingShow>
        <DressroomMyfittingShow></DressroomMyfittingShow>
        <DressroomMyfittingShow></DressroomMyfittingShow>
      </div>
    </>
  );
}
