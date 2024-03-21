import React from 'react';

type contentType = {
  id: number;
  content: string;
};

type DropDownProps = {
  open: boolean;
  title: string;
  contents: contentType[];
  selectFunction: (value: number) => void;
};

export default function DropDown({ open, title, contents, selectFunction }: DropDownProps) {
  return (
    <>
      {open ? (
        <div className='flex border-2 rounded'>{title}</div>
      ) : (
        <div className='flex border-2 first:rounded last:rounded'>
          <div>{title}</div>
          {contents.map((c, index) => (
            <div key={index}>{c.content}</div>
          ))}
        </div>
      )}
    </>
  );
}
