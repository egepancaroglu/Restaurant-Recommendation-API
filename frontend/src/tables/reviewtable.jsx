import { userUrl } from '@/constant';
import MutateReview from '@/mutate/mutatereview';
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

const ReviewTable = () => {
  const [reviews, setReviews] = useState([]);

  const fetchReviews = useCallback(() => {
    axios.get(userUrl + '/reviews').then((res) => {
      setReviews(res.data.data);
    });
  }, []);

  useEffect(() => {
    fetchReviews();
  }, [fetchReviews]);

  return (
    <Flex gap={12}>
      <MutateReview fetchReviews={fetchReviews} />
      <TableContainer>
        <Table variant='simple'>
          <Thead>
            <Tr>
              <Th>Id</Th>
              <Th>Yorum</Th>
              <Th>Puan</Th>
              <Th>Kullanıcı Id</Th>
              <Th>Restoran Id</Th>
            </Tr>
          </Thead>
          <Tbody>
            {reviews.map((review) => {
              return (
                <Tr key={review.id}>
                  <Td>{review.id}</Td>
                  <Td>{review.comment}</Td>
                  <Td>{review.rate}</Td>
                  <Td>{review.userId}</Td>
                  <Td>{review.restaurantId}</Td>
                </Tr>
              );
            })}
          </Tbody>
        </Table>
      </TableContainer>
    </Flex>
  );
};

export default ReviewTable;
