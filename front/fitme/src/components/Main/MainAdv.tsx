import React from 'react';
import { Carousel } from 'flowbite-react';
import adv01 from '../../assets/images/adv/adv01.png';
import adv02 from '../../assets/images/adv/adv02.png';

export default function MainAdv() {
  return (
    <div>
      <Carousel className='h-80'>
        <img src={adv01} alt='advertisement01' />
        <img src={adv02} alt='advertisement02' />
      </Carousel>
    </div>
  );
}
