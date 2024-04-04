import Noimage from '../../assets/images/noimage.png';

type ReviewProps = {
  review: {
    id: number;
    rating: number;
    content: string;
    imageUrl: string;
    memberNickname: string;
    createdAt: Date;
  };
};

const ReviewOne: React.FC<ReviewProps> = ({ review }) => {
  // Date 객체로 변환
  const createdAtDate = new Date(review.createdAt);

  // Intl.DateTimeFormat을 사용하여 한국 시간대로 포맷팅
  const koreanTime = new Intl.DateTimeFormat('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit',
    hour12: false,
    timeZone: 'Asia/Seoul',
  }).format(createdAtDate);

  return (
    <>
      <div className='flex flex-col my-5'>
        <div className='flex justify-between'>
          <span className='text-xs'>
            작성자 : <span className='font-semibold'>{review.memberNickname}</span>
          </span>
          <span className='text-xs'>
            평점 :<span className='text-yellow-300'>{'★'.repeat(review.rating)}</span>
          </span>
        </div>
        <div className='flex mt-2'>
          <img
            className='aspect-[3/4] w-20 border-beige border-2'
            src={review.imageUrl ? review.imageUrl.replace('./', 'https://fit-me.site/') : Noimage}
            alt=''
          />
          <div className='ml-4'>
            <div className='text-xs mb-2'>
              <div className='text-xs mb-2'>{koreanTime}</div>
            </div>
            <div className='text-sm'>{review.content}</div>
          </div>
        </div>
      </div>
    </>
  );
};

export default ReviewOne;
