import React from 'react';
import FeedList from '../components/Feed/FeedList';
import User from '../components/Feed/User';
import Plus from '../components/Feed/Plus';

export default function MyFeedPage() {
  return (
    <div>
      <User></User>
      <Plus></Plus>
      <FeedList></FeedList>
    </div>
  );
}
