import { useState } from 'react';
import RatingStars from '../../components/MyPage/RatingStars';
import { useLocation, useNavigate } from 'react-router-dom';
import { api } from '../../services/api';

const Review = () => {
  const navigate = useNavigate();
  const location = useLocation(); // useLocation Hook을 사용합니다.
  const { id, name, url, brandName } = location.state;
  const [rating, setRating] = useState(0);
  const [reviewText, setReviewText] = useState('');
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [imagePreviewUrl, setImagePreviewUrl] = useState<string | null>(null); // 이미지 프리뷰 URL을 저장할 상태

  const submitReview = async () => {
    console.log(id);
    // rating이 0이거나 리뷰 텍스트가 10자 이하인 경우 경고 메시지를 표시합니다.
    if (rating === 0 && reviewText.length < 10 && selectedFile === null) {
      alert('모든 정보를 작성해주세요.');
      return;
    } else if (rating === 0) {
      alert('별점을 주세요.');
      return;
    } else if (reviewText.length < 10) {
      alert('리뷰를 10자 이상 작성해주세요.');
      return;
    } else if (selectedFile === null) {
      alert('사진을 등록해주세요.');
      return;
    }
    const formData = new FormData();
    // productReviewRequest JSON 객체를 생성하고 이를 문자열로 변환
    const productReviewRequest = {
      rating: rating,
      content: reviewText,
    };
    formData.append(
      'productReviewRequest',
      new Blob([JSON.stringify(productReviewRequest)], { type: 'application/json' })
    );

    formData.append('image', selectedFile);

    // axios
    try {
      await api.post(`/api/products/${id}/reviews`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      alert('리뷰 등록 완료!');
      navigate('/mypage?tab=bought');
    } catch (error) {
      console.error('리뷰 제출 중 에러 발생:', error);
    }
  };

  // 파일 선택 핸들러
  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files && event.target.files[0];
    if (file) {
      // 파일 크기가 20MB 이하인지 확인합니다.
      const maxSize = 20 * 1024 * 1024; // 20MB
      if (file.size > maxSize) {
        // 파일 크기가 20MB를 초과하면 경고 메시지를 띄웁니다.
        alert('파일 크기가 20MB를 초과합니다. 다른 파일을 선택해 주세요.');
      } else {
        // 파일 크기가 적절하면 선택된 파일을 처리합니다.
        setSelectedFile(file);

        // 선택된 파일로부터 이미지 프리뷰 URL을 생성합니다.
        const reader = new FileReader();
        reader.onloadend = () => {
          setImagePreviewUrl(reader.result as string);
        };
        reader.readAsDataURL(file);
      }
    }
  };

  // "사진 첨부" 버튼 클릭 핸들러
  const handleAttachClick = () => {
    // 숨겨진 file input을 찾아서 클릭 이벤트를 발생시킵니다.
    document.getElementById('fileInput')?.click();
  };
  return (
    <>
      <div className='w-full flex justify-center py-6'>
        <div className='bg-white w-5/6 h-48 rounded-xl border-4 border-black '>
          <div className='flex justify-around p-5 gap-4'>
            <div className='w-28 aspect-[3/4] border-black border-2 rounded-md'>
              <img className='w-full h-full object-cover rounded-md' src={url} alt='상품 이미지' />
            </div>
            <div className='flex flex-col w-80'>
              <div className='text-lg font-semibold mb-4'>{brandName}</div>
              <div>{name}</div>
            </div>
          </div>
        </div>
      </div>
      <RatingStars onRatingChange={setRating} />
      <div className='flex flex-col gap-3 items-center justify-center'>
        <div className='mt-5 text-2xl'>리뷰를 남겨주세요.</div>
        <textarea
          placeholder='10자 이상 입력해주세요.'
          className='resize-none w-4/5 h-32 bg-gray-300 rounded-sm p-3 my-10'
          onChange={(e) => setReviewText(e.target.value)}
        ></textarea>
        {/* <button className='w-4/5 h-10 bg-white rounded-lg flex justify-center items-center border-2 border-gray-400 hover:shadow-lg hover-shadow-darkgray focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none'> */}
        <input
          type='file'
          id='fileInput'
          style={{ display: 'none' }} // input을 숨깁니다.
          accept='.jpg, .jpeg, .png, .gif' // 허용하는 파일 형식
          onChange={handleFileChange} // 파일이 선택되면 handleFileChange를 호출합니다.
        />
        {imagePreviewUrl ? (
          // 이미지 프리뷰 URL이 있으면 이미지를 보여주고, 없으면 버튼을 표시합니다.
          <>
            <img src={imagePreviewUrl} alt='Preview' style={{ width: '100%', height: 'auto' }} />
            <button
              className='w-4/5 h-10 bg-white rounded-lg flex justify-center items-center border-2 border-gray-400 hover:shadow-lg focus:opacity-[0.85] active:opacity-[0.85]'
              onClick={handleAttachClick} // 이 버튼을 클릭하면 숨겨진 input이 클릭됩니다.
            >
              <svg
                xmlns='http://www.w3.org/2000/svg'
                fill='none'
                viewBox='0 0 24 24'
                strokeWidth={1.5}
                stroke='currentColor'
                className='w-6 h-6'
              >
                <path
                  strokeLinecap='round'
                  strokeLinejoin='round'
                  d='m2.25 15.75 5.159-5.159a2.25 2.25 0 0 1 3.182 0l5.159 5.159m-1.5-1.5 1.409-1.409a2.25 2.25 0 0 1 3.182 0l2.909 2.909m-18 3.75h16.5a1.5 1.5 0 0 0 1.5-1.5V6a1.5 1.5 0 0 0-1.5-1.5H3.75A1.5 1.5 0 0 0 2.25 6v12a1.5 1.5 0 0 0 1.5 1.5Zm10.5-11.25h.008v.008h-.008V8.25Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Z'
                />
              </svg>
              <div className='mx-3'>사진 재선택 (20MB 이하)</div>
            </button>
          </>
        ) : (
          <button
            className='w-4/5 h-10 bg-white rounded-lg flex justify-center items-center border-2 border-gray-400 hover:shadow-lg focus:opacity-[0.85] active:opacity-[0.85]'
            onClick={handleAttachClick} // 이 버튼을 클릭하면 숨겨진 input이 클릭됩니다.
          >
            <svg
              xmlns='http://www.w3.org/2000/svg'
              fill='none'
              viewBox='0 0 24 24'
              strokeWidth={1.5}
              stroke='currentColor'
              className='w-6 h-6'
            >
              <path
                strokeLinecap='round'
                strokeLinejoin='round'
                d='m2.25 15.75 5.159-5.159a2.25 2.25 0 0 1 3.182 0l5.159 5.159m-1.5-1.5 1.409-1.409a2.25 2.25 0 0 1 3.182 0l2.909 2.909m-18 3.75h16.5a1.5 1.5 0 0 0 1.5-1.5V6a1.5 1.5 0 0 0-1.5-1.5H3.75A1.5 1.5 0 0 0 2.25 6v12a1.5 1.5 0 0 0 1.5 1.5Zm10.5-11.25h.008v.008h-.008V8.25Zm.375 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Z'
              />
            </svg>
            <div className='mx-3'>사진 첨부 (20MB 이하)</div>
          </button>
        )}
        <button
          data-twe-ripple-init
          data-twe-ripple-color='black'
          className='w-4/5 h-10 bg-bluegray rounded-lg text-white hover:shadow-lg focus:opacity-[0.85] focus:shadow-none active:opacity-[0.85] active:shadow-none mb-10'
          onClick={submitReview}
        >
          리뷰 등록
        </button>
      </div>
    </>
  );
};

export default Review;
