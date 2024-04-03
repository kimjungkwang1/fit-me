import SearchHistoryKeyword from './SearchHistoryKeyword';

type SearchType = {
  name: string;
  date: string;
};

export default function SearchHistory() {
  return (
    <div>
      {localStorage.getItem('recent') &&
        JSON.parse(localStorage.getItem('recent')!)
          .reverse()
          .map((search: SearchType, index: number) => (
            <SearchHistoryKeyword key={index} keyword={search.name} date={search.date} />
          ))}
    </div>
  );
}
