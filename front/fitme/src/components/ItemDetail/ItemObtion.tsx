import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

type SizeType = {
  id: number;
  size: string;
  stockQuantity: number;
};

type ColorType = {
  id: number;
  color: string;
  sizes: SizeType[];
};

type SelectedType = {
  idColor: number;
  color: string;
  idSize: number;
  size: string;
};

export default function ItemOption() {
  // 옵션 선택창 오픈 함수
  const [openModal, setOpenModal] = useState(false);
  const modalHandler = () => {
    setOpenModal(!openModal);
  };

  // 옵션 선택 드롭다운 오픈 함수
  const [openColor, setOpenColor] = useState(false);
  const [openSize, setOpenSize] = useState(false);
  const openColorHandler = () => {
    setOpenSize(false);
    setOpenColor(!openColor);
  };
  const openSizeHandler = () => {
    setOpenColor(false);
    setOpenSize(!openSize);
  };

  // api 호출
  const [options, setOptions] = useState<ColorType[]>();
  // const { item_id } = useParams();
  // useEffect(() => {
  //   axios.get(`http://j10a306.p.ssafy.io:8080/api/products/${item_id}/options`).then(({ data }) => {
  //     setOptions(data);
  //   });
  // }, [item_id]);
  useEffect(() => {
    setOptions([
      {
        id: 1,
        color: 'RED',
        sizes: [
          {
            id: 1,
            size: 'S',
            stockQuantity: 5,
          },
          {
            id: 2,
            size: 'M',
            stockQuantity: 6,
          },
        ],
      },
      {
        id: 2,
        color: 'BLUE',
        sizes: [
          {
            id: 3,
            size: 'S',
            stockQuantity: 7,
          },
        ],
      },
      {
        id: 3,
        color: 'GREEN',
        sizes: [
          {
            id: 3,
            size: 'S',
            stockQuantity: 7,
          },
        ],
      },
      {
        id: 4,
        color: 'ORANGE',
        sizes: [
          {
            id: 3,
            size: 'S',
            stockQuantity: 7,
          },
        ],
      },
      {
        id: 5,
        color: 'PURPLE',
        sizes: [
          {
            id: 3,
            size: 'S',
            stockQuantity: 7,
          },
        ],
      },
      {
        id: 6,
        color: 'BLACK',
        sizes: [
          {
            id: 3,
            size: 'S',
            stockQuantity: 7,
          },
        ],
      },
      {
        id: 7,
        color: 'PINK',
        sizes: [
          {
            id: 3,
            size: 'S',
            stockQuantity: 7,
          },
        ],
      },
    ]);
  }, []);

  // 선택된 옵션 관리
  const [selected, setSelected] = useState<SelectedType>();
  const [sizeOptions, setSizeOptions] = useState<SizeType[]>();
  const handleColor = ({ id, color, sizes }: ColorType) => {
    setSelected({
      idColor: id,
      color: color,
      idSize: 0,
      size: '',
    });
    setSizeOptions(sizes);
  };
  const handleSize = ({ id, size }: SizeType) => {
    setSelected((prev) => ({
      ...prev!,
      idSize: id,
      size: size,
    }));
  };

  return (
    <>
      <div className='flex justify-center'>
        {/* modal */}
        {openModal && (
          <div className='flex-grow'>
            <div
              onClick={modalHandler}
              className='bg-black opacity-30 fixed top-0 bottom-0 w-[100vw] max-w-[600px]'
            />
            <div className='bg-white p-4 flex flex-col bottom-[53.6px] h-[50vh] max-h-80 w-full max-w-[600px] fixed rounded-t-2xl overflow-scroll'>
              <span className='text-sm font-semibold'>옵션 선택</span>

              {/* 색상 선택 창 */}
              {openColor ? (
                // 선택 창 열려있을 때
                <ul onClick={openColorHandler} className='mt-2 text-sm w-full'>
                  <li className='border-2 first:rounded-t-lg last:rounded-b-lg px-3 py-2 text-sm w-full'>
                    {selected && selected.color ? selected.color : '색상'}
                  </li>
                  {options &&
                    options.map((opt, index) => (
                      <li
                        className='border-2 border-y-0 last:border-b-2 first:rounded-t-lg last:rounded-b-lg px-3 py-2 text-sm w-full'
                        key={index}
                        onClick={() => handleColor(opt)}
                      >
                        {opt.color}
                      </li>
                    ))}
                </ul>
              ) : (
                // 선택 창 닫혀있을 때
                <div
                  onClick={openColorHandler}
                  className='mt-2 border-2 rounded-lg px-3 py-2 text-sm w-full'
                >
                  {selected && selected.color ? selected.color : '색상'}
                </div>
              )}

              {/* 사이즈 선택 창 */}
              {openSize ? (
                // 선택 창 열려있을 때
                <ul onClick={openSizeHandler} className='mt-2 text-sm w-full'>
                  <li className='border-2 first:rounded-t-lg last:rounded-b-lg px-3 py-2 text-sm w-full'>
                    {selected && selected.size ? selected.size : '사이즈'}
                  </li>
                  {sizeOptions &&
                    sizeOptions.map((opt, index) => (
                      <li
                        className='border-2 border-y-0 last:border-b-2 first:rounded-t-lg last:rounded-b-lg px-3 py-2 text-sm w-full flex justify-between'
                        key={index}
                        onClick={() => handleSize(opt)}
                      >
                        <span>{opt.size}</span>
                        <span className='text-xs'>{opt.stockQuantity}개</span>
                      </li>
                    ))}
                </ul>
              ) : (
                // 선택 창 닫혀있을 때
                <div
                  onClick={openSizeHandler}
                  className='mt-2 border-2 rounded-lg px-3 py-2 text-sm w-full'
                >
                  {selected && selected.size ? selected.size : '사이즈'}
                </div>
              )}
            </div>
          </div>
        )}

        {/* 하단 버튼 */}
        <div className='bg-white p-2 flex flex-row fixed bottom-0 w-full max-w-[600px]'>
          {/* 입어보기 버튼 */}
          <div className='w-full mr-2 bg-white border border-solid border-black rounded-lg px-2 py-3 text-center text-sm font-semibold'>
            <span>입어보기(장바구니)</span>
          </div>

          {/* 구매하기 버튼 */}
          <div
            onClick={modalHandler}
            className='w-full bg-bluegray border border-solid border-black rounded-lg px-2 py-3 text-center text-white text-sm font-semibold'
          >
            <span>구매하기</span>
          </div>
        </div>
      </div>
    </>
  );
}
