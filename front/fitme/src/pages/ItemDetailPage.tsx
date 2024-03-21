import ItemInfo from '../components/ItemDetail/ItemInfo';
import RecommendedItems from '../components/ItemDetail/RecommendedItems';
import ItemDetailImg from '../components/ItemDetail/ItemDetailImg';
import ItemReview from '../components/ItemDetail/ItemReview';
import ItemOption from '../components/ItemDetail/ItemObtion';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

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
    axios.get(`http://j10a306.p.ssafy.io:8080/api/products/${item_id}`).then(({ data }) => {
      setItem(data);
    });
  }, [item_id]);

  return (
    <div className='mb-[53.6px]'>
      {item && (
        <ItemInfo
          mainImages={item.mainImages}
          likeCount={item.likeCount}
          liked={item.liked}
          brand={item.brand}
          name={item.name}
          price={item.price}
          tags={item.tags}
        />
      )}

      {item && <ItemDetailImg detailImages={item.detailImages} />}
      <RecommendedItems />
      <ItemReview />
      <ItemOption />
    </div>
  );
}
