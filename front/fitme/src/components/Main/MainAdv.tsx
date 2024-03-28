import { Carousel } from 'flowbite-react';
import adv01 from '../../assets/images/adv/adv01.jpg';
import adv02 from '../../assets/images/adv/adv02.jpg';
import adv03 from '../../assets/images/adv/adv03.jpg';

// 이미지와 텍스트 정보를 포함하는 배열
const images = [
  {
    src: adv01,
    alt: 'advertisement01',
    text: <></>,
  },
  { src: adv02, alt: 'advertisement02', text: <></> },
  { src: adv03, alt: 'advertisement03', text: <></> },
];

export default function MainAdv() {
  return (
    <div>
      <Carousel className=''>
        {images.map((image, index) => (
          <div key={index} className='relative'>
            <img src={image.src} alt={image.alt} className='object-cover' />
            {/* 이미지 위에 텍스트를 표시합니다. */}
            {/* <div className='absolute top-0 left-0 right-0 bottom-0 flex items-end justify-end'>
              <div className='mr-12 mb-20 text-4xl'>
                <div className='text-white text-center drop-shadow-lg'>{image.text}</div>
              </div>
            </div> */}
          </div>
        ))}
      </Carousel>
    </div>
  );
}
