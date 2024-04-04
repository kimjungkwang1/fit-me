import MainAdv from '../components/Main/MainAdv';
import MainItemList from '../components/Main/MainItemList';

export default function MainPage() {
  return (
    <div className='flex flex-col gap-4'>
      <div>
        <MainAdv />
      </div>
      <div>
        <MainItemList />
      </div>
    </div>
  );
}
