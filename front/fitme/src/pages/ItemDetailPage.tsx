import ItemInfo from '../components/ItemDetail/ItemInfo';
import RecommendedItems from '../components/ItemDetail/RecommendedItems';
import ItemDetailImg from '../components/ItemDetail/ItemDetailImg';
import ItemReview from '../components/ItemDetail/ItemReview';
import ItemOption from '../components/ItemDetail/ItemOption';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { api } from '../services/api';

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

type ItemType = {
  id: number;
  name: string;
  price: number;
  mainImages: ImageType[];
  brand: BrandType;
  likeCount: number;
  reviewRating: number;
  reviewCount: number;
  detailImages: ImageType[];
  liked: boolean;
  tags: TagType[];
};

export default function ItemDetailPage() {
  const [item, setItem] = useState<ItemType>();
  const { item_id } = useParams();
  useEffect(() => {
    api.get(`https://fit-me.site/api/products/${item_id}`).then(({ data }) => {
      setItem(data);
    });

    // 우클릭 방지 메서드
    const handleContextMenu = (event: MouseEvent) => {
      event.preventDefault();
      alert('상품설명 무단도용방지를 위하여 마우스 오른쪽 버튼은 사용하실 수 없습니다.');
    };

    document.addEventListener('contextmenu', handleContextMenu);
    return () => {
      document.removeEventListener('contextmenu', handleContextMenu);
    };
  }, [item_id]);

  return (
    <>
      {item && (
        <div className='mb-[53.6px]'>
          <ItemInfo
            id={item.id}
            mainImages={item.mainImages}
            initialLiked={item.liked}
            brand={item.brand}
            name={item.name}
            price={item.price}
            tags={item.tags}
          />
          <ItemDetailImg detailImages={item.detailImages} />
          <RecommendedItems id={item.id} />
          <ItemReview
            id={item.id}
            reviewCount={item.reviewCount}
            reviewRating={item.reviewRating}
          />
          <ItemOption price={item.price} />
        </div>
      )}
    </>
  );
}
