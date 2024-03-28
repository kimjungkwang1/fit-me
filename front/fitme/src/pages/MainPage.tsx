import MainAdv from '../components/Main/MainAdv';
import MainMyItems from '../components/Main/MainMyItems';
import MainItemList from '../components/Main/MainItemList';
import { isAuthenticated } from '../services/auth';

export default function MainPage() {
  return (
    <div className='flex flex-col gap-4'>
      <div>
        <MainAdv />
      </div>
      <div>{isAuthenticated() && <MainMyItems />}</div>
      <div>
        <MainItemList />
      </div>
    </div>
  );
}
