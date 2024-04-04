type ButtonProps = {
  id: number;
  name: string;
  selected: boolean;
  handler: (id: number) => void;
};

export default function FilterButton({ id, name, selected, handler }: ButtonProps) {
  return (
    <>
      {selected ? (
        <div
          key={id}
          onClick={() => handler(id)}
          className='mr-[3px] inline-block px-3 py-1 text-xs text-center border border-solid border-gray-500 bg-gray-700 text-white rounded-lg'
        >
          {name}
        </div>
      ) : (
        <div
          key={id}
          onClick={() => handler(id)}
          className='mr-[3px] inline-block px-3 py-1 text-xs text-center border border-solid border-gray-400 rounded-lg'
        >
          {name}
        </div>
      )}
    </>
  );
}
