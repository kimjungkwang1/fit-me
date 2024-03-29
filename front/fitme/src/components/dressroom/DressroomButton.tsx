import React, { useState, useRef } from 'react';
import { Modal, Button } from 'flowbite-react';
import { useSelector } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { useDispatch } from 'react-redux';
// import { CiCirclePlus } from 'react-icons/ci';

export default function DressroomButton() {
  // const [openSaveModal, setOpenSaveModal] = useState<boolean>(false);
  const [openChangeModal, setOpenChangeModal] = useState<boolean>(false);
  // const [selectedTab, setSelectedTab] = useState<'sample' | 'mine'>('sample');
  const inputRef = useRef<HTMLInputElement>(null);

  const handleInput = () => {
    inputRef.current?.click();
  };

  const models = useSelector((state: RootState) => state.dressroom.models);
  return (
    <>
      <div className='flex justify-center'>
        <Button className='w-[40%] mx-1' color='gray' onClick={() => setOpenChangeModal(true)}>
          <span className='text-xs'>사진변경</span>
        </Button>
      </div>

      <Modal show={openChangeModal} popup onClose={() => setOpenChangeModal(false)}>
        <Modal.Header>사진 변경</Modal.Header>
        <Modal.Body>
          <div className='p-1'>
            <div className='flex flex-col text-center border-gray-200 shadow-sm sticky top-0 z-50'>
              <div className='flex'>
                <div className='w-[100%] p-4 border-r border-gray-200 cursor-pointer bg-white'>
                  샘플사진
                </div>
              </div>
            </div>
            <div className='grid grid-cols-2 mt-2'>
              {models.map((item) => (
                <img
                  className='w-[60%] h-auto object-contain mx-auto '
                  src={item.url}
                  alt='상의'
                ></img>
              ))}
            </div>
          </div>
        </Modal.Body>
      </Modal>
    </>
  );
}
