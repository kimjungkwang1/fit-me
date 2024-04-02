import { Carousel } from 'flowbite-react';
import CarouselTheme from '../../style/CarouselTheme';
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
      <Carousel theme={CarouselTheme}>
        {images.map((image, index) => (
          <div key={index} className='relative'>
            <img src={image.src} alt={image.alt} className='object-cover' />
          </div>
        ))}
      </Carousel>
    </div>
  );
}
