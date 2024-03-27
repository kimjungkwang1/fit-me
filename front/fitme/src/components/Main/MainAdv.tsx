import React from 'react';
import { Carousel } from 'flowbite-react';
import adv01 from '../../assets/images/adv/adv01.png';
import adv02 from '../../assets/images/adv/adv02.png';
import adv03 from '../../assets/images/adv/adv03.png';
import adv04 from '../../assets/images/adv/adv04.png';

export default function MainAdv() {
  return (
    <div>
      <Carousel className='h-80'>
        <img src={adv01} alt='advertisement01' />
        <img src={adv02} alt='advertisement02' />
        <img src={adv03} alt='advertisement03' />
        <img src={adv04} alt='advertisement04' />
      </Carousel>
    </div>
  );
}
