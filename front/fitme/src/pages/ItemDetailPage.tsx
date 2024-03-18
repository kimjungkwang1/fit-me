import ItemInfo from '../components/ItemDetail/ItemInfo';
import RecommendedItems from '../components/ItemDetail/RecommendedItems';
import ItemDetailImg from '../components/ItemDetail/ItemDetailImg';
import ItemReview from '../components/ItemDetail/ItemReview';
import PurchaseBtn from '../components/ItemDetail/PurchaseBtn';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

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
  mainImageUrl: string[];
  brand: BrandType;
  likeCount: number;
  reviewRating: number;
  reviewCount: number;
  detailImageUrl: string[];
  liked: boolean;
  tags: TagType;
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
          mainImageUrl={item.mainImageUrl}
          likeCount={item.likeCount}
          liked={item.liked}
          brand={item.brand}
          name={item.name}
          price={item.price}
          tags={item.tags}
        />
      )}
      <ItemDetailImg />
      <RecommendedItems />
      <ItemReview />
      <PurchaseBtn />
    </div>
  );
}
