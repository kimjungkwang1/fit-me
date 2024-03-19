import { useState } from 'react';
import { TbThumbUpFilled } from 'react-icons/tb';
import Tags from '../Common/Tags';
import { Carousel } from 'flowbite-react';

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
  mainImages: ImageType[];
  likeCount: number;
  liked: boolean;
  brand: BrandType;
  name: string;
  price: number;
  tags: TagType[];
};

export default function ItemInfo({
  mainImages,
  likeCount,
  liked,
  brand,
  name,
  price,
  tags,
}: ItemInfoProps) {
  // 좋아요 토글 기능
  const [like, setLike] = useState(false);
  const likeHandler = () => {
    setLike(!like);
  };

  return (
    <>
      <div>
        {/* 상품 이미지 */}
        <div className='flex w-full aspect-square bg-white justify-center relative'>
          <Carousel pauseOnHover slide={false}>
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
          {like ? (
            // true
            <TbThumbUpFilled
              onClick={likeHandler}
              className='absolute bottom-[5px] right-[5px] w-10 h-10 text-darkgray bg-bluegray rounded-full p-1'
            />
          ) : (
            // false
            <TbThumbUpFilled
              onClick={likeHandler}
              className='absolute bottom-[5px] right-[5px] w-10 h-10 text-white bg-bluegray rounded-full p-1'
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
