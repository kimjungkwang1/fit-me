import { RxCross2 } from 'react-icons/rx';

type FilterSelectedProps = {
  id: number;
  name: string;
  handler: (id: number) => void;
};

export default function FilterSelected({ id, name, handler }: FilterSelectedProps) {
  return (
    <>
      <div
        key={id}
        className='flex flex-row items-center mr-[3px] px-3 py-1 text-xs text-center border border-solid border-gray-400 bg-gray-200 rounded-lg'
      >
        {name}&nbsp;
        <RxCross2 onClick={() => handler(id)} />
      </div>
    </>
  );
}
