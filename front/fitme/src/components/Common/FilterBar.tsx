import { Button, Modal } from 'flowbite-react';
import React, { useState } from 'react';

export default function FilterBar() {
  const [openModal, setOpenModal] = useState(false);

  return (
    <div className='flex flex-row gap-1 mx-[3%] mt-2 mb-1'>
      <Button color='light' size='xs' onClick={() => setOpenModal(true)}>
        브랜드
      </Button>
      <Button color='light' size='xs' onClick={() => setOpenModal(true)}>
        카테고리
      </Button>
      <Button color='light' size='xs' onClick={() => setOpenModal(true)}>
        가격
      </Button>
      <Modal show={openModal} popup>
        <Modal.Body>
          <div>
            <span className='flex font-semibold mt-2 mb-1'>브랜드</span>
            <div className='flex flex-row flex-wrap gap-2'>
              <Button size='xs' color='light'>
                브랜드이름
              </Button>
              <Button size='xs' color='light'>
                브랜드이름
              </Button>
              <Button size='xs' color='light'>
                브랜드이름
              </Button>
              <Button size='xs' color='light'>
                브랜드이름
              </Button>
              <Button size='xs' color='light'>
                브랜드이름
              </Button>
              <Button size='xs' color='light'>
                브랜드이름
              </Button>
              <Button size='xs' color='light'>
                브랜드이름
              </Button>
              <Button size='xs' color='light'>
                브랜드이름
              </Button>
              <Button size='xs' color='light'>
                브랜드이름
              </Button>
            </div>
          </div>
          <div>
            <span className='flex font-semibold mt-2 mb-1'>카테고리</span>
            <div className='flex flex-row flex-wrap gap-2'>
              <Button size='xs' color='light'>
                카테고리이름
              </Button>
              <Button size='xs' color='light'>
                카테고리이름
              </Button>
              <Button size='xs' color='light'>
                카테고리이름
              </Button>
              <Button size='xs' color='light'>
                카테고리이름
              </Button>
              <Button size='xs' color='light'>
                카테고리이름
              </Button>
              <Button size='xs' color='light'>
                카테고리이름
              </Button>
              <Button size='xs' color='light'>
                카테고리이름
              </Button>
            </div>
          </div>
          <div>
            <span className='flex font-semibold mt-2 mb-1'>가격</span>
          </div>
          <Button size='sm' onClick={() => setOpenModal(false)}>
            적용
          </Button>
        </Modal.Body>
      </Modal>
    </div>
  );
}
