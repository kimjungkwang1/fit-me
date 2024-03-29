import React, { useState, useEffect } from 'react';
import { Modal, Button } from 'flowbite-react';
import { useSelector } from 'react-redux';
import { RootState, AppDispatch } from '../../store/store';
import { useDispatch } from 'react-redux';
import { setModel, makeFittings, getModels } from '../../store/dressroomSlice';

// import { CiCirclePlus } from 'react-icons/ci';

export default function DressroomButton() {
  // const [openSaveModal, setOpenSaveModal] = useState<boolean>(false);
  const [openChangeModal, setOpenChangeModal] = useState<boolean>(false);
  const [shouldMakeFittings, setShouldMakeFittings] = useState<boolean>(false);
  // const [selectedTab, setSelectedTab] = useState<'sample' | 'mine'>('sample');

  const dispatch = useDispatch<AppDispatch>();

  const models = useSelector((state: RootState) => state.dressroom.models);
  useEffect(() => {
    const makeFittingIfNeeded = async () => {
      if (shouldMakeFittings) {
        await dispatch(makeFittings());
        setShouldMakeFittings(false); // 동작 완료 후 상태 초기화
      }
    };
    makeFittingIfNeeded();
  }, [shouldMakeFittings, dispatch]);

  useEffect(() => {
    dispatch(getModels());
  }, []);

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
                  key={item.id}
                  className='w-[60%] h-auto object-contain mx-auto '
                  src={item.url}
                  alt='모델'
                  onClick={() => {
                    dispatch(setModel({ id: item.id, url: item.url }));
                    setOpenChangeModal(false);
                    setShouldMakeFittings(true);
                  }}
                ></img>
              ))}
            </div>
          </div>
        </Modal.Body>
      </Modal>
    </>
  );
}
