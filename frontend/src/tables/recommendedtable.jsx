import { recommendUrl } from '@/constant';
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
  Input,
  FormControl,
  FormLabel,
  Stack,
  Button,
} from '@chakra-ui/react';
import axios from 'axios';
import { useState } from 'react';

const RecommendedTable = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [userId, setUserId] = useState(0);

  const fetchRestaurants = () => {
    axios
      .get(recommendUrl + `/restaurants/recommendRestaurants/${userId}`)
      .then((res) => {
        setRestaurants(res.data.data);
      });
  };

  return (
    <Flex gap={12}>
      <Stack>
        <FormControl>
          <FormLabel>Kullanıcı Id</FormLabel>
          <Input
            value={userId}
            onChange={(e) => {
              setUserId(Number(e.target.value));
            }}
          />
        </FormControl>
        <Button onClick={fetchRestaurants}>Getir</Button>
      </Stack>
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

export default RecommendedTable;
