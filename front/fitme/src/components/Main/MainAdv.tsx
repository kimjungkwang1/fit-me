import React from 'react';
import { Carousel } from 'flowbite-react';
import adv01 from '../../assets/images/adv/adv01.png';
import adv02 from '../../assets/images/adv/adv02.png';
import adv03 from '../../assets/images/adv/adv03.png';
import adv04 from '../../assets/images/adv/adv04.png';

// 이미지와 텍스트 정보를 포함하는 배열
const images = [
  {
    src: adv01,
    alt: 'advertisement01',
    text: (
      <>
        2024 S/S 신상품 <br /> ~30% 할인{' '}
      </>
    ),
  },
  { src: adv02, alt: 'advertisement02', text: <>광고 2 설명</> },
  { src: adv03, alt: 'advertisement03', text: <>광고 3 설명</> },
  { src: adv04, alt: 'advertisement04', text: <>광고 4 설명</> },
];

export default function MainAdv() {
  return (
    <div>
      <Carousel className='h-80'>
        {images.map((image, index) => (
          <div key={index} className='relative'>
            <img src={image.src} alt={image.alt} />
            {/* 이미지 위에 텍스트를 표시합니다. */}
            <div className='absolute top-0 left-0 right-0 bottom-0 flex items-end justify-end'>
              <div className='mr-12 mb-20 text-4xl'>
                <div className='text-white text-center drop-shadow-lg'>{image.text}</div>
              </div>
            </div>
          </div>
        ))}
      </Carousel>
    </div>
  );
}
