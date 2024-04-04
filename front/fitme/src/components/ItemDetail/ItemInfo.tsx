import { TbThumbUp, TbThumbUpFilled } from 'react-icons/tb';
import Tags from '../Common/Tags';
import { Carousel } from 'flowbite-react';
import CarouselTheme from '../../style/CarouselTheme';
import { isAuthenticated } from '../../services/auth';
import { api } from '../../services/api';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

type ImageType = {
  id: number;
  url: string;
};

type BrandType = {
  id: number;
  name: string;
};

type TagType = {
  id: number;
  name: string;
};

type ItemInfoProps = {
  id: number;
  mainImages: ImageType[];
  initialLiked: boolean;
  brand: BrandType;
  name: string;
  price: number;
  tags: TagType[];
};

export default function ItemInfo({
  id,
  mainImages,
  initialLiked,
  brand,
  name,
  price,
  tags,
}: ItemInfoProps) {
  const navigate = useNavigate();

  // 좋아요 토글 기능
  const [liked, setLiked] = useState<boolean>(initialLiked);
  const likeHandler = () => {
    // 로그인이 안돼있으면 로그인 페이지로 보내주면서 함수 실행하지 않음
    if (!isAuthenticated()) {
      navigate('/login');
      return;
    } else {
      if (liked) {
        // 이미 좋아요 눌러져있는 상태일 때 - 좋아요 취소
        api.delete(`/api/products/${id}/like`);
        setLiked(false);
      } else {
        // 좋아요가 안 눌러져 있을 때 - 좋아요 등록
        api.post(`/api/products/${id}/like`);
        setLiked(true);
      }
    }
  };

  return (
    <>
      <div>
        {/* 상품 이미지 */}
        <div className='flex w-full aspect-square bg-white justify-center relative'>
          <Carousel theme={CarouselTheme}>
            {mainImages.map((img, index) => (
              <img
                src={img.url}
                alt='product_image'
                key={index}
                className='object-contain h-full'
              />
            ))}
          </Carousel>

          {/* like button */}
          {liked ? (
            // true
            <TbThumbUpFilled
              onClick={likeHandler}
              className='absolute bottom-[5px] right-[5px] w-10 h-10 text-gray-800 p-1'
            />
          ) : (
            // false
            <TbThumbUp
              onClick={likeHandler}
              className='absolute bottom-[5px] right-[5px] w-10 h-10 text-gray-800 p-1'
            />
          )}
        </div>

        {/* 상품 정보 */}
        <div className='m-[3%]'>
          <div>
            <span className='text-sm font-semibold'>
              {/* 브랜드 이름 */}
              {brand.name}
            </span>
          </div>
          <div>
            <span className='text-lg font-semibold'>
              {/* 상품 이름 */}
              {name}
            </span>
          </div>
          <div>
            <span className='text-2xl font-bold'>
              {/* 상품 가격 */}
              {price.toLocaleString()}원
            </span>
          </div>
          <div>
            {tags.map((tag, index) => (
              <Tags key={index} tag={tag.name} />
            ))}
          </div>
        </div>
      </div>
    </>
  );
}
