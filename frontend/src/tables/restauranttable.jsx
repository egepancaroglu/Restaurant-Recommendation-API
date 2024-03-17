import { restaurantUrl } from '@/constant';
import MutateRestaurant from '@/mutate/mutaterestaurant';
import {
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
  Flex,
} from '@chakra-ui/react';
import axios from 'axios';
import { useCallback, useState, useEffect } from 'react';

const RestaurantTable = () => {
  const [restaurants, setRestaurants] = useState([]);

  const fetchRestaurants = useCallback(() => {
    axios.get(restaurantUrl + '/restaurants').then((res) => {
      setRestaurants(res.data.data);
    });
  }, []);

  useEffect(() => {
    fetchRestaurants();
  }, [fetchRestaurants]);

  return (
    <Flex gap={12}>
      <MutateRestaurant fetchRestaurants={fetchRestaurants} />
      <TableContainer>
        <Table variant='simple'>
          <Thead>
            <Tr>
              <Th>Id</Th>
              <Th>Ad</Th>
              <Th>Konum</Th>
            </Tr>
          </Thead>
          <Tbody>
            {restaurants.map((restaurant) => {
              return (
                <Tr key={restaurant.id}>
                  <Td>{restaurant.id}</Td>
                  <Td>{restaurant.name}</Td>
                  <Td>{restaurant.location}</Td>
                </Tr>
              );
            })}
          </Tbody>
        </Table>
      </TableContainer>
    </Flex>
  );
};

export default RestaurantTable;
