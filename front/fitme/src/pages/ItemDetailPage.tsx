import ItemInfo from '../components/ItemDetail/ItemInfo';
import RecommendedItems from '../components/ItemDetail/RecommendedItems';
import ItemDetailImg from '../components/ItemDetail/ItemDetailImg';
import ItemReview from '../components/ItemDetail/ItemReview';
import PurchaseBtn from '../components/ItemDetail/PurchaseBtn';

export default function ItemDetailPage() {
  return (
    <div className='mb-[53.6px]'>
      <ItemInfo />
      <ItemDetailImg />
      <RecommendedItems />
      <ItemReview />
      <PurchaseBtn />
    </div>
  );
}
