import React from 'react';
import { Tabs, TabList, TabPanels, Tab, TabPanel } from '@chakra-ui/react';
import UserTable from '@/tables/usertable';
import RestaurantTable from '@/tables/restauranttable';
import ReviewTable from '@/tables/reviewtable';
import RecommendedTable from '@/tables/recommendedtable';

const Home = () => {
  return (
    <Tabs>
      <TabList>
        <Tab>Kullanıcılar</Tab>
        <Tab>Restoranlar</Tab>
        <Tab>Önerilenler</Tab>
        <Tab>Yorumlar</Tab>
      </TabList>

      <TabPanels>
        <TabPanel>
          <UserTable />
        </TabPanel>
        <TabPanel>
          <RestaurantTable />
        </TabPanel>
        <TabPanel>
          <RecommendedTable />
        </TabPanel>
        <TabPanel>
          <ReviewTable />
        </TabPanel>
      </TabPanels>
    </Tabs>
  );
};

export default Home;
