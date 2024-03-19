type ImageType = {
  id: number;
  url: string;
};

type ItemDetailImgProps = {
  detailImages: ImageType[];
};

export default function ItemDetailImg({ detailImages }: ItemDetailImgProps) {
  return (
    <>
      <div>
        {/* 상품 상세 이미지 */}
        {detailImages.map((img, index) => (
          <img src={img.url} alt='detail_image' key={index} className='w-full' />
        ))}
      </div>
    </>
  );
}
