import React from 'react';
import { Tabs } from 'flowbite-react';
import FeedList from './FeedList';

export default function FeedTab() {
  return (
    <>
      <Tabs style='fullWidth'>
        <Tabs.Item active title='매거진'>
          <FeedList></FeedList>
        </Tabs.Item>
        <Tabs.Item title='유저피드'>
          <FeedList></FeedList>
        </Tabs.Item>
      </Tabs>
    </>
  );
}
